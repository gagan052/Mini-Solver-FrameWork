type NavigationView = "site" | "template-editor"

type NavigationProps = {
  activeView: NavigationView
  onChange: (view: NavigationView) => void
}

export function Navigation({
  activeView,
  onChange,
}: NavigationProps) {
  return (
    <nav className="navigation">
      <div className="navigation-brand">
        Mini Solver
      </div>

      <div className="navigation-items">
        <button
          className={
            activeView === "site"
              ? "navigation-item active"
              : "navigation-item"
          }
          onClick={() => onChange("site")}
        >
          Site
        </button>

        <button
          className={
            activeView === "template-editor"
              ? "navigation-item active"
              : "navigation-item"
          }
          onClick={() => onChange("template-editor")}
        >
          Template Editor
        </button>
      </div>
    </nav>
  )
}