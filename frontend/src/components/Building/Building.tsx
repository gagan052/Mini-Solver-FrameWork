import type { BuildingInstance } from "../../models/BuildingInstance"
import type { BuildingTemplate } from "../../templates/buildingTemplates"
import { resolveBuildingGeometry } from "../../templates/resolveBuildingGeometry"

type BuildingProps = {
  instance: BuildingInstance
  template: BuildingTemplate
}

export function Building({ instance, template }: BuildingProps) {
  const geometry = resolveBuildingGeometry(instance, template)

  const {
    width,
    depth,
    height,
  } = geometry

  return (
    <mesh
      position={[
        instance.position.x,
        instance.position.y + height / 2,
        instance.position.z,
      ]}
    >
      <boxGeometry args={[width, height, depth]} />
      <meshStandardMaterial />
    </mesh>
  )
}