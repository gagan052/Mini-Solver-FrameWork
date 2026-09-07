import { useMemo } from "react"
import type { Solid3D } from "../../geometry/types"
import { solid3DToBufferGeometry } from "../../geometry/geometryAdapter"

type BackendBuildingProps = {
  solid: Solid3D
  position: {
    x: number
    y: number
    z: number
  }
}

export function BackendBuilding({
  solid,
  position,
}: BackendBuildingProps) {
  const geometry = useMemo(
    () => solid3DToBufferGeometry(solid),
    [solid]
  )

  const box = geometry.boundingBox

  const offsetX = box
    ? (box.max.x - box.min.x) / 2
    : 0

  const offsetZ = box
    ? (box.max.z - box.min.z) / 2
    : 0

  return (
    <mesh
      geometry={geometry}
      position={[
        position.x - offsetX,
        position.y,
        position.z - offsetZ,
      ]}
    >
      <meshStandardMaterial />
    </mesh>
  )
}