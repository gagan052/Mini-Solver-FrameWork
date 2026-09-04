import { useEffect, useMemo } from "react";
import type { Solid3D } from "../../geometry/types";
import { solid3DToBufferGeometry } from "../../geometry/geometryAdapter";

type GeneratedBuildingProps = {
  geometry: Solid3D;
};

export function GeneratedBuilding({
  geometry,
}: GeneratedBuildingProps) {

  const bufferGeometry = useMemo(
    () => solid3DToBufferGeometry(geometry),
    [geometry]
  );

  useEffect(() => {
    return () => {
      bufferGeometry.dispose();
    };
  }, [bufferGeometry]);

  return (
    <mesh geometry={bufferGeometry}>
      <meshStandardMaterial />
    </mesh>
  );
}