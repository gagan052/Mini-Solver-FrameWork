import type { Solid3D } from "../geometry/types"
import type { BuildingInstance } from "./BuildingInstance"

export type SceneObject = {
  objectId: string
  objectType: "Building"

  instance: BuildingInstance

  geometry?: Solid3D


  status: "PLACED" | "GENERATED"
}