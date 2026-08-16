import { OrbitControls } from "@react-three/drei";
import { Building } from "../components/Building/Building";
import type { BuildingInstance } from "../models/BuildingInstance";
import type { BuildingTemplate } from "../templates/buildingTemplates";

type SceneProps = {
  building: BuildingInstance;
  template: BuildingTemplate;
};

export function Scene({
  building,
  template,
}: SceneProps) {
  return (
    <>
      <directionalLight position={[5, 5, 5]} />

      <gridHelper args={[40, 40]} />

      <axesHelper args={[5]} />

      <Building
        instance={building}
        template={template}
      />

      <OrbitControls />
    </>
  );
}