import type { BuildingTemplate } from "../../templates/buildingTemplates"
import * as THREE from "three";

type BuildingDragPreviewProps = {
  template: BuildingTemplate
  position: {
    x: number
    y: number
    z: number
  }
}

export function BuildingDragPreview({
  template,
  position,
}: BuildingDragPreviewProps) {

  const {
    width,
    depth,
  } = template.defaultGeometry

  return (
    <mesh
      position={[
        position.x,
        0.08,
        position.z,
      ]}
      rotation={[
        -Math.PI / 2,
        0,
        0,
      ]}
    >
      <planeGeometry
        args={[
          width,
          depth,
        ]}
      />

      <meshBasicMaterial
        color="skyblue"
        transparent
        opacity={0.45}
        side={THREE.DoubleSide}
      />
    </mesh>
  )
}