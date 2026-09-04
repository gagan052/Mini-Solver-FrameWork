import * as THREE from "three";
import type { Solid3D, Point3D } from "./types";

function addTriangle(
  positions: number[],
  a: Point3D,
  b: Point3D,
  c: Point3D
) {
  positions.push(
    a.x, a.z, a.y,
    b.x, b.z, b.y,
    c.x, c.z, c.y
  );
}

export function solid3DToBufferGeometry(
  solid: Solid3D
): THREE.BufferGeometry {
  const positions: number[] = [];

  for (const face of solid.faces) {
    const vertices = face.vertices;

    if (vertices.length < 3) {
      continue;
    }

    // Triangulate the face while preserving
    // the vertex ordering supplied by the backend.
    for (let i = 1; i < vertices.length - 1; i++) {
      addTriangle(
        positions,
        vertices[0],
        vertices[i],
        vertices[i + 1]
      );
    }
  }

  const geometry = new THREE.BufferGeometry();

  geometry.setAttribute(
    "position",
    new THREE.Float32BufferAttribute(
      positions,
      3
    )
  );

  geometry.computeVertexNormals();
  geometry.computeBoundingBox();
  geometry.computeBoundingSphere();

  return geometry;
}