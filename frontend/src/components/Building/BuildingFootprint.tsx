import type { BuildingInstance } from "../../models/BuildingInstance"
import * as THREE from "three"

type BuildingFootprintProps = {
  instance: BuildingInstance
  selected: boolean
}

export function BuildingFootprint({
  instance,
  selected,
}: BuildingFootprintProps) {
  const {
    width,
    depth,
  } = instance.geometry

  return (
    <mesh
      position={[
        instance.position.x,
        0.05,
        instance.position.z,
      ]}
      rotation={[-Math.PI / 2, 0, 0]}
    >
      <planeGeometry
        args={[width, depth]}
      />

      <meshBasicMaterial
        transparent
        opacity={selected ? 0.55 : 0.35}
        color={selected ? "orange" : "skyblue"}
        side={THREE.DoubleSide}
      />
    </mesh>
  )
}