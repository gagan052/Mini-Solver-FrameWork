import type { BuildingInstance } from "../models/BuildingInstance";
import type { BuildingTemplate } from "./buildingTemplates";

export function createBuildingInstance(
  template: BuildingTemplate,
  id: string,
  position = { x: 0, y: 0, z: 0 }
): BuildingInstance {
  return {
    id,
    templateId: template.id,
    position,
    geometry: template.defaultGeometry,
  };
}