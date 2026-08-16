import { useState } from "react";
import { Canvas } from "@react-three/fiber";

import { Scene } from "./scene/Scene";
import { BuildingEditor } from "./components/BuildingEditor/BuildingEditor";

import { buildingTemplates } from "./templates/buildingTemplates";
import { createBuildingInstance } from "./templates/createBuildingInstance";

import "./App.css";

function App() {
  const template = buildingTemplates[0];

  const [building, setBuilding] = useState(() =>
    createBuildingInstance(template, "building-1")
  );

  return (
    <div className="app">

      <Canvas
        camera={{
          position: [40, 30, 40],
          fov: 50,
        }}
      >
        <Scene
          building={building}
          template={template}
        />
      </Canvas>

      <BuildingEditor
        instance={building}
        onChange={setBuilding}
      />

    </div>
  );
}

export default App;