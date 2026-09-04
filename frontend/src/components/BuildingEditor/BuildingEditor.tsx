import type { BuildingInstance } from "../../models/BuildingInstance";
import type { SolverStatus } from "../../types/SolverStatus";

type BuildingEditorProps = {
  instance: BuildingInstance;
  onChange: (instance: BuildingInstance) => void;
  onGenerate: () => void;
  solverStatus: SolverStatus;
};

export function BuildingEditor({
  instance,
  onChange,
  onGenerate,
  solverStatus,
}: BuildingEditorProps) {
  const updateGeometry = (
    property: "width" | "depth" | "height",
    value: number
  ) => {
    onChange({
      ...instance,

      geometry: {
        ...instance.geometry,
        [property]: value,
      },
    });
  };

  return (
    <div className="building-editor">
      <strong>Building</strong>

      <label>
        Width
        <input
          type="number"
          value={instance.geometry.width}
          onChange={(event) =>
            updateGeometry("width", Number(event.target.value))
          }
        />
      </label>

      <label>
        Depth
        <input
          type="number"
          value={instance.geometry.depth}
          onChange={(event) =>
            updateGeometry("depth", Number(event.target.value))
          }
        />
      </label>

      <label>
        Height
        <input
          type="number"
          value={instance.geometry.height}
          onChange={(event) =>
            updateGeometry("height", Number(event.target.value))
          }
        />
      </label>

      <button
        type="button"
        onClick={onGenerate}
        disabled={solverStatus === "GENERATING"}
      >
        {solverStatus === "GENERATING" ? "Generating..." : "Generate Building"}
      </button>

      <div className="solver-status">Status: {solverStatus}</div>
    </div>
  );
}
