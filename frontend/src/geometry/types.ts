export type Point3D = {
  x: number;
  y: number;
  z: number;
};

export type Face3D = {
  vertices: Point3D[];
};

export type Solid3D = {
  vertices: Point3D[];
  faces: Face3D[];
};
