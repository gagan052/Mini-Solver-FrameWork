export type BuildingTemplate = {
  id: string
  name: string
  defaultGeometry: {
    type: "BOX"
    width: number
    depth: number
    height: number
  }
}

export const buildingTemplates: BuildingTemplate[] = [
  {
    id: "standard-building",
    name: "Standard Building",
    defaultGeometry: {
      type: "BOX",
      width: 20,
      depth: 30,
      height: 10,
    },
  },
  {
    id: "large-warehouse",
    name: "Large Warehouse",
    defaultGeometry: {
      type: "BOX",
      width: 60,
      depth: 40,
      height: 15,
    },
  },
]