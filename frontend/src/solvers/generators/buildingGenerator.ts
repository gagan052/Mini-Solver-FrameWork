import * as THREE from "three";

export interface BuildingTemplate {
  id: string;
  name: string;
  type: string;
  geometry: {
    width: number;
    depth: number;
    height: number;
  };
}

export function generateBuilding(
  template: BuildingTemplate
): THREE.Mesh {
  const { width, depth, height } = template.geometry;

  const geometry = new THREE.BoxGeometry(
    width,
    height,
    depth
  );

  const material = new THREE.MeshStandardMaterial({
    color: 0x808080
  });

  return new THREE.Mesh(geometry, material);
}