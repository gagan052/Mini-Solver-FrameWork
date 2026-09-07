import { OrbitControls } from "@react-three/drei";

import { BackendBuilding } from "../components/Building/BackendBuilding";
import { BuildingFootprint } from "../components/Building/BuildingFootprint";
import { SiteDropTarget } from "./SiteDropTarget";

import type { SceneObject } from "../models/SceneObject";

type SceneProps = {
  objects: Record<string, SceneObject>;

  selectedBuildingId: string | null;
  draggedTemplateId: string | null;
  onSelectBuilding: (id: string) => void;

  onTemplateDrop: (
    templateId: string,
    position: {
      x: number;
      y: number;
      z: number;
    }
  ) => void;

  onTemplateDrag: (
    templateId: string,
    position: {
      x: number;
      y: number;
      z: number;
    }
  ) => void;

  dragPreview: {
    templateId: string;
    position: {
      x: number;
      y: number;
      z: number;
    };
    width: number;
    depth: number;
  } | null;
};

export function Scene({
  objects,
  selectedBuildingId,
  onSelectBuilding,
  onTemplateDrop,
  onTemplateDrag,
  dragPreview,
  draggedTemplateId,
}: SceneProps) {
  return (
    <>
      <ambientLight intensity={0.5} />

      <directionalLight position={[20, 30, 20]} intensity={1} />

      <gridHelper args={[100, 100]} />

      <axesHelper args={[5]} />

      {Object.values(objects).map((object) => {
        /*
         * Generated object → backend geometry → 3D
         */
        if (object.status === "GENERATED" && object.geometry) {
          return (
            <group
              key={object.objectId}
              onClick={(event) => {
                event.stopPropagation();
                onSelectBuilding(object.objectId);
              }}
            >
              <BackendBuilding
                solid={object.geometry}
                position={object.instance.position}
              />
            </group>
          );
        }

        /*
         * Placed object → 2D footprint
         */
        return (
          <group
            key={object.objectId}
            onClick={(event) => {
              event.stopPropagation();
              onSelectBuilding(object.objectId);
            }}
          >
            <BuildingFootprint
              instance={object.instance}
              selected={object.objectId === selectedBuildingId}
            />
          </group>
        );
      })}

      <SiteDropTarget
        onTemplateDrop={onTemplateDrop}
        onTemplateDrag={onTemplateDrag}
        draggedTemplateId={draggedTemplateId}
      />

      {dragPreview && (
        <BuildingFootprint
          instance={{
            id: "drag-preview",
            templateId: dragPreview.templateId,
            position: dragPreview.position,
            geometry: {
              type: "BOX",
              width: dragPreview.width,
              depth: dragPreview.depth,
              height: 10,
            },
            status: "PLACED",
          }}
          selected={false}
        />
      )}

      {/*
       * Camera is always user controlled.
       *
       * No switching between 2D and 3D.
       */}
      <OrbitControls makeDefault enableDamping />
    </>
  );
}
