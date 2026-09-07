import type { BuildingTemplate } from "./buildingTemplates"
import { buildingTemplates as defaultTemplates } from "./buildingTemplates"

const TEMPLATE_STORAGE_KEY =
  "mini-solver-building-templates"

export function loadTemplates(): BuildingTemplate[] {
  const stored = localStorage.getItem(
    TEMPLATE_STORAGE_KEY
  )

  if (!stored) {
    return defaultTemplates
  }

  try {
    const parsed = JSON.parse(stored)

    if (!Array.isArray(parsed)) {
      return defaultTemplates
    }

    return parsed
  } catch {
    return defaultTemplates
  }
}

export function saveTemplate(
  template: BuildingTemplate
): BuildingTemplate[] {
  const templates = loadTemplates()

  const existingIndex = templates.findIndex(
    (item) => item.id === template.id
  )

  let updatedTemplates: BuildingTemplate[]

  if (existingIndex >= 0) {
    updatedTemplates = [...templates]

    updatedTemplates[existingIndex] = template
  } else {
    updatedTemplates = [
      ...templates,
      template,
    ]
  }

  localStorage.setItem(
    TEMPLATE_STORAGE_KEY,
    JSON.stringify(updatedTemplates)
  )

  return updatedTemplates
}