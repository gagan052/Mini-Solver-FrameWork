import { useMemo } from "react";
import type { Solid3D } from "../../geometry/types";
import { solid3DToBufferGeometry } from "../../geometry/geometryAdapter";

type BackendBuildingProps = {
  solid: Solid3D;
};

export function BackendBuilding({
  solid,
}: BackendBuildingProps) {
  const geometry = useMemo(
    () => solid3DToBufferGeometry(solid),
    [solid]
  );

  return (
    <mesh geometry={geometry}>
      <meshStandardMaterial />
    </mesh>
  );
}