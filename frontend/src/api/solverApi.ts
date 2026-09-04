export type EventCommand =
  | "ADD"
  | "UPDATE"
  | "DELETE";

export type EventRequest = {
  command: EventCommand;
  objectType: string;
  objectId: string;
  data?: Record<string, unknown>;
};

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

export type SolverResponse = {
  success: boolean;
  message: string;
  data: Solid3D;
};

const API_BASE_URL = "http://localhost:8080";

export async function dispatchEvent(
  event: EventRequest
): Promise<SolverResponse> {
  const response = await fetch(
    `${API_BASE_URL}/api/events`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(event),
    }
  );

  if (!response.ok) {
    throw new Error(
      `Backend request failed: ${response.status}`
    );
  }

  return response.json();
}