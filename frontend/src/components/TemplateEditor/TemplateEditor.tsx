import { useEffect, useState } from "react"
import type { BuildingTemplate } from "../../templates/buildingTemplates"

type TemplateEditorProps = {
  templates: BuildingTemplate[]
  onSave: (template: BuildingTemplate) => void
}

const newTemplate: BuildingTemplate = {
  id: "new-building",
  name: "New Building",
  defaultGeometry: {
    type: "BOX",
    width: 20,
    depth: 30,
    height: 10,
  },
}

export function TemplateEditor({
  templates,
  onSave,
}: TemplateEditorProps) {
  const [selectedTemplateId, setSelectedTemplateId] =
    useState<string | null>(
      templates[0]?.id ?? null
    )

  const [json, setJson] = useState("")

  const [error, setError] = useState<string | null>(
    null
  )

  const [success, setSuccess] = useState<string | null>(
    null
  )

  /*
   * Load the selected template into the editor.
   */
  useEffect(() => {
    const template = templates.find(
      (item) => item.id === selectedTemplateId
    )

    if (!template) {
      return
    }

    setJson(
      JSON.stringify(template, null, 2)
    )

    setError(null)
    setSuccess(null)
  }, [selectedTemplateId, templates])

  /*
   * Select an existing template.
   */
  const handleSelectTemplate = (
    templateId: string
  ) => {
    setSelectedTemplateId(templateId)
    setError(null)
    setSuccess(null)
  }

  /*
   * Start creating a new template.
   */
  const handleNewTemplate = () => {
    setSelectedTemplateId(null)

    setJson(
      JSON.stringify(newTemplate, null, 2)
    )

    setError(null)
    setSuccess(null)
  }

  /*
   * Validate the JSON without saving.
   */
  const handleValidate = () => {
    setError(null)
    setSuccess(null)

    try {
      const parsed = JSON.parse(json)

      validateTemplate(parsed)

      setSuccess("Template JSON is valid.")
    } catch (error) {
      setError(
        error instanceof Error
          ? error.message
          : "Invalid template JSON."
      )
    }
  }

  /*
   * Validate and save the template.
   */
  const handleSave = () => {
    setError(null)
    setSuccess(null)

    try {
      const parsed = JSON.parse(json)

      validateTemplate(parsed)

      onSave(parsed)

      setSelectedTemplateId(parsed.id)

      setSuccess(
        `Template "${parsed.name}" saved successfully.`
      )
    } catch (error) {
      setError(
        error instanceof Error
          ? error.message
          : "Unable to save template."
      )
    }
  }

  return (
    <div className="template-editor-page">

      <div className="template-editor-header">
        <div>
          <h1>Template Editor</h1>

          <p>
            Create and edit reusable building
            templates.
          </p>
        </div>
      </div>

      <div className="template-editor-workspace">

        {/* =========================
            Template List
           ========================= */}

        <aside className="template-list-panel">

          <div className="template-list-header">
            <h2>Templates</h2>

            <button
              type="button"
              className="new-template-button"
              onClick={handleNewTemplate}
            >
              + New Template
            </button>
          </div>

          <div className="template-list">

            {templates.map((template) => (
              <button
                key={template.id}
                type="button"
                className={
                  selectedTemplateId === template.id
                    ? "template-list-item selected"
                    : "template-list-item"
                }
                onClick={() =>
                  handleSelectTemplate(
                    template.id
                  )
                }
              >
                <div className="template-list-icon">
                  🏢
                </div>

                <div className="template-list-content">
                  <strong>
                    {template.name}
                  </strong>

                  <span>
                    {template.id}
                  </span>
                </div>
              </button>
            ))}

          </div>
        </aside>

        {/* =========================
            JSON Editor
           ========================= */}

        <main className="template-json-panel">

          <div className="template-json-header">
            <div>
              <h2>
                {selectedTemplateId
                  ? "Edit Template"
                  : "New Template"}
              </h2>

              <span>
                Building Template
              </span>
            </div>
          </div>

          <textarea
            value={json}
            onChange={(event) => {
              setJson(event.target.value)

              setError(null)
              setSuccess(null)
            }}
            spellCheck={false}
            className="template-json-editor"
          />

          <div className="template-editor-actions">

            <button
              type="button"
              onClick={handleValidate}
              className="template-button secondary"
            >
              Validate
            </button>

            <button
              type="button"
              onClick={handleSave}
              className="template-button primary"
            >
              Save Template
            </button>

          </div>

          {error && (
            <div className="template-message error">
              {error}
            </div>
          )}

          {success && (
            <div className="template-message success">
              {success}
            </div>
          )}

          <div className="template-editor-help">

            <h3>Template Structure</h3>

            <pre>
{`{
  "id": "office-building",
  "name": "Office Building",
  "defaultGeometry": {
    "type": "BOX",
    "width": 40,
    "depth": 30,
    "height": 15
  }
}`}
            </pre>

          </div>

        </main>
      </div>
    </div>
  )
}


function validateTemplate(
  template: unknown
): asserts template is BuildingTemplate {

  if (
    !template ||
    typeof template !== "object"
  ) {
    throw new Error(
      "Template must be a JSON object."
    )
  }

  const value =
    template as Record<string, unknown>

  if (
    typeof value.id !== "string" ||
    value.id.trim() === ""
  ) {
    throw new Error(
      'Template "id" must be a non-empty string.'
    )
  }

  if (
    typeof value.name !== "string" ||
    value.name.trim() === ""
  ) {
    throw new Error(
      'Template "name" must be a non-empty string.'
    )
  }

  if (
    !value.defaultGeometry ||
    typeof value.defaultGeometry !== "object"
  ) {
    throw new Error(
      'Template must contain "defaultGeometry".'
    )
  }

  const geometry =
    value.defaultGeometry as Record<
      string,
      unknown
    >

  if (geometry.type !== "BOX") {
    throw new Error(
      'defaultGeometry.type must be "BOX".'
    )
  }

  if (
    typeof geometry.width !== "number" ||
    geometry.width <= 0
  ) {
    throw new Error(
      "defaultGeometry.width must be greater than 0."
    )
  }

  if (
    typeof geometry.depth !== "number" ||
    geometry.depth <= 0
  ) {
    throw new Error(
      "defaultGeometry.depth must be greater than 0."
    )
  }

  if (
    typeof geometry.height !== "number" ||
    geometry.height <= 0
  ) {
    throw new Error(
      "defaultGeometry.height must be greater than 0."
    )
  }
}