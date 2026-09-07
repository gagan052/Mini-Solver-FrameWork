import type { BuildingTemplate } from "../../templates/buildingTemplates";

type TemplateLibraryProps = {
  templates: BuildingTemplate[];
  onTemplateDragStart: (templateId: string) => void;
};

// type TemplateDragEvent = CustomEvent<{
//   templateId: string;
// }>;

export function TemplateLibrary({
  templates,
  onTemplateDragStart,
}: TemplateLibraryProps) {
  const handleDragStart = (
    event: React.DragEvent,
    template: BuildingTemplate
  ) => {
    event.dataTransfer.setData("application/template-id", template.id);

    event.dataTransfer.effectAllowed = "copy";

    // Hide the browser's default HTML drag preview
    const dragImage = document.createElement("canvas");
    dragImage.width = 1;
    dragImage.height = 1;

    event.dataTransfer.setDragImage(dragImage, 0, 0);

    onTemplateDragStart(template.id);
  };

  const handleDragEnd = () => {
    window.dispatchEvent(new CustomEvent("template-drag-end"));
  };

  return (
    <aside className="template-library">
      <h2>Templates</h2>

      {templates.map((template) => (
        <div
          key={template.id}
          className="template-card"
          draggable
          onDragStart={(event) => handleDragStart(event, template)}
          onDragEnd={handleDragEnd}
        >
          <div className="template-card-icon">🏢</div>

          <div>
            <strong>{template.name}</strong>

            <div className="template-card-description">Drag to site</div>
          </div>
        </div>
      ))}
    </aside>
  );
}
