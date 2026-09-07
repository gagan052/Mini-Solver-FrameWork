import { useState } from "react";
import { Canvas } from "@react-three/fiber";

import { Scene } from "./scene/Scene";
import { BuildingEditor } from "./components/BuildingEditor/BuildingEditor";
import { TemplateLibrary } from "./components/TemplateLibrary/TemplateLibrary";

// import { buildingTemplates } from "./templates/buildingTemplates";
import { createBuildingInstance } from "./templates/createBuildingInstance";
import { Navigation } from "./components/Navigation/Navigation";
import { TemplateEditor } from "./components/TemplateEditor/TemplateEditor";

import { loadTemplates, saveTemplate } from "./templates/templateStorage";

import type { BuildingTemplate } from "./templates/buildingTemplates";

import { dispatchEvent } from "./api/solverApi";

import type { BuildingInstance } from "./models/BuildingInstance";
import type { SceneObject } from "./models/SceneObject";
import type { SolverStatus } from "./types/SolverStatus";

import "./App.css";

function App() {
  const [buildingInstances, setBuildingInstances] = useState<
    BuildingInstance[]
  >([]);

  const [selectedBuildingId, setSelectedBuildingId] = useState<string | null>(
    null
  );

  const [solverStatus, setSolverStatus] = useState<SolverStatus>("IDLE");

  const [draggedTemplateId, setDraggedTemplateId] = useState<string | null>(
    null
  );

  const [sceneObjects, setSceneObjects] = useState<Record<string, SceneObject>>(
    {}
  );

  const [templates, setTemplates] = useState<BuildingTemplate[]>(() =>
    loadTemplates()
  );

  const [activeView, setActiveView] = useState<"site" | "template-editor">(
    "site"
  );

  const [dragPreview, setDragPreview] = useState<{
    templateId: string;
    position: {
      x: number;
      y: number;
      z: number;
    };
    width: number;
    depth: number;
  } | null>(null);

  const selectedBuilding = buildingInstances.find(
    (building) => building.id === selectedBuildingId
  );

  const handleTemplateDrop = (
    templateId: string,
    position: {
      x: number;
      y: number;
      z: number;
    }
  ) => {
    const template = templates.find((template) => template.id === templateId);

    if (!template) {
      return;
    }

    const id = `building-${Date.now()}`;

    const instance = createBuildingInstance(template, id, position);

    setBuildingInstances((current) => [...current, instance]);

    setSceneObjects((current) => ({
      ...current,
      [id]: {
        objectId: id,
        objectType: "Building",
        instance,
        status: "PLACED",
      },
    }));

    setDragPreview(null);
    setDraggedTemplateId(null);
    setSelectedBuildingId(id);
  };

  const handleTemplateDrag = (
    templateId: string,
    position: {
      x: number;
      y: number;
      z: number;
    }
  ) => {
    const template = templates.find((template) => template.id === templateId);

    if (!template) {
      return;
    }

    setDragPreview({
      templateId,
      position,
      width: template.defaultGeometry.width,
      depth: template.defaultGeometry.depth,
    });
  };

  const handleTemplateSave = (template: BuildingTemplate) => {
    const updatedTemplates = saveTemplate(template);

    setTemplates(updatedTemplates);
  };

  const handleGenerate = async () => {
    if (!selectedBuilding) {
      return;
    }

    setSolverStatus("GENERATING");

    try {
      const response = await dispatchEvent({
        command: "ADD",
        objectType: "Building",
        objectId: selectedBuilding.id,

        criteria : {
          width: selectedBuilding.geometry.width,
          depth: selectedBuilding.geometry.depth,
          height: selectedBuilding.geometry.height,
        },
      });

      console.log("Solver response:", response);

      if (response.success) {
        setSceneObjects((current) => ({
          ...current,

          [selectedBuilding.id]: {
            objectId: selectedBuilding.id,
            objectType: "Building",

            instance: {
              ...selectedBuilding,
              status: "GENERATED",
            },

            geometry: response.data,

            status: "GENERATED",
          },
        }));

        setBuildingInstances((current) =>
          current.map((building) =>
            building.id === selectedBuilding.id
              ? {
                  ...building,
                  status: "GENERATED",
                }
              : building
          )
        );

        setSolverStatus("COMPLETED");
      } else {
        setSolverStatus("FAILED");
      }
    } catch (error) {
      console.error("Failed to generate building:", error);

      setSolverStatus("FAILED");
    }
  };

  return (
    <div className="app">
      <Navigation activeView={activeView} onChange={setActiveView} />

      {activeView === "site" && (
        <>
          <Canvas
            camera={{
              position: [40, 30, 40],
              fov: 50,
            }}
          >
            <Scene
              objects={sceneObjects}
              selectedBuildingId={selectedBuildingId}
              onSelectBuilding={setSelectedBuildingId}
              onTemplateDrop={handleTemplateDrop}
              onTemplateDrag={handleTemplateDrag}
              dragPreview={dragPreview}
              draggedTemplateId={draggedTemplateId}
            />
          </Canvas>

          {buildingInstances.length > 0 && (
            <div className="building-selector">
              {buildingInstances.map((building) => (
                <button
                  key={building.id}
                  onClick={() => setSelectedBuildingId(building.id)}
                >
                  {building.id}
                </button>
              ))}
            </div>
          )}

          {selectedBuilding && (
            <BuildingEditor
              instance={selectedBuilding}
              onChange={(updatedBuilding) => {
                setBuildingInstances((current) =>
                  current.map((building) =>
                    building.id === updatedBuilding.id
                      ? updatedBuilding
                      : building
                  )
                );

                setSceneObjects((current) => ({
                  ...current,
                  [updatedBuilding.id]: {
                    ...current[updatedBuilding.id],
                    instance: updatedBuilding,
                  },
                }));
              }}
              onGenerate={handleGenerate}
              solverStatus={solverStatus}
            />
          )}

          <TemplateLibrary
            templates={templates}
            onTemplateDragStart={setDraggedTemplateId}
          />
        </>
      )}

      {activeView === "template-editor" && (
        <TemplateEditor templates={templates} onSave={handleTemplateSave} />
      )}
    </div>
  );
}

export default App;
