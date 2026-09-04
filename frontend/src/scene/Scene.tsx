import { OrbitControls } from "@react-three/drei";
import { BackendBuilding } from "../components/Building/BackendBuilding";
import type { Solid3D } from "../geometry/types";

type SceneProps = {
  solid: Solid3D | null;
};

export function Scene({ solid }: SceneProps) {
  return (
    <>
      <directionalLight position={[5, 5, 5]} />

      <gridHelper args={[40, 40]} />

      <axesHelper args={[5]} />

      {solid && <BackendBuilding solid={solid} />}

      <OrbitControls />
    </>
  );
}