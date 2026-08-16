import type { BuildingInstance } from "../models/BuildingInstance"
import type { BuildingTemplate } from "./buildingTemplates"

export function resolveBuildingGeometry(
  instance: BuildingInstance,
  template: BuildingTemplate
) {
  return instance.geometry ?? template.defaultGeometry
}