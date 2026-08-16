import type { BuildingTemplate } from "../templates/buildingTemplates"

export type BuildingInstance = {
  id: string
  templateId: BuildingTemplate["id"]

  geometry: {
    type: "BOX"
    width: number
    depth: number
    height: number
  }

  position: {
    x: number
    y: number
    z: number
  }
}