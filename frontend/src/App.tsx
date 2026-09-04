import { useState } from "react";
import { Canvas } from "@react-three/fiber";

import { Scene } from "./scene/Scene";
import { BuildingEditor } from "./components/BuildingEditor/BuildingEditor";

import { buildingTemplates } from "./templates/buildingTemplates";
import { createBuildingInstance } from "./templates/createBuildingInstance";

import { dispatchEvent } from "./api/solverApi";
import type { Solid3D } from "./geometry/types";
import type { SolverStatus } from "./types/SolverStatus";

import "./App.css";

function App() {
  const template = buildingTemplates[0];

  const [building, setBuilding] = useState(() =>
    createBuildingInstance(template, "building-1")
  );

  const [solverStatus, setSolverStatus] =
    useState<SolverStatus>("IDLE");

  const [generatedGeometry, setGeneratedGeometry] =
    useState<Solid3D | null>(null);

  const handleGenerate = async () => {
    setSolverStatus("GENERATING");

    try {
      const response = await dispatchEvent({
        command: "ADD",
        objectType: "Building",
        objectId: building.id,
        data: {
          width: building.geometry.width,
          depth: building.geometry.depth,
          height: building.geometry.height,
        },
      });

      console.log("Solver response:", response);

      if (response.success) {
        setGeneratedGeometry(response.data);
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
      <Canvas
        camera={{
          position: [40, 30, 40],
          fov: 50,
        }}
      >
        <Scene solid={generatedGeometry} />
      </Canvas>

      <BuildingEditor
        instance={building}
        onChange={setBuilding}
        onGenerate={handleGenerate}
        solverStatus={solverStatus}
      />
    </div>
  );
}

export default App;