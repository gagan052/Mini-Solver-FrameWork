import { useEffect } from "react";
import { useThree } from "@react-three/fiber";
import * as THREE from "three";

type Position = {
  x: number;
  y: number;
  z: number;
};

type SiteDropTargetProps = {
  onTemplateDrop: (templateId: string, position: Position) => void;

  onTemplateDrag: (templateId: string, position: Position) => void;

  draggedTemplateId: string | null;
};

export function SiteDropTarget({
  onTemplateDrop,
  onTemplateDrag,
  draggedTemplateId,
}: SiteDropTargetProps) {
  const { camera, gl } = useThree();

  useEffect(() => {
    const canvas = gl.domElement;

    const getGroundPosition = (event: DragEvent): Position | null => {
      const rect = canvas.getBoundingClientRect();

      const mouse = new THREE.Vector2(
        ((event.clientX - rect.left) / rect.width) * 2 - 1,
        -((event.clientY - rect.top) / rect.height) * 2 + 1
      );

      const raycaster = new THREE.Raycaster();

      raycaster.setFromCamera(mouse, camera);

      /*
       * Three.js ground plane.
       *
       * y = 0 is our site plane.
       */
      const groundPlane = new THREE.Plane(new THREE.Vector3(0, 1, 0), 0);

      const position = new THREE.Vector3();

      const hit = raycaster.ray.intersectPlane(groundPlane, position);

      if (!hit) {
        return null;
      }

      return {
        x: position.x,
        y: 0,
        z: position.z,
      };
    };

    const handleDragOver = (event: DragEvent) => {
      event.preventDefault();

      if (event.dataTransfer) {
        event.dataTransfer.dropEffect = "copy";
      }

      const templateId = draggedTemplateId;

      if (!templateId) {
        return;
      }

      const position = getGroundPosition(event);

      if (!position) {
        return;
      }

      onTemplateDrag(templateId, position);
    };

    const handleDrop = (event: DragEvent) => {
      event.preventDefault();

      const templateId = event.dataTransfer?.getData("application/template-id");

      if (!templateId) {
        return;
      }

      const position = getGroundPosition(event);

      if (!position) {
        return;
      }

      onTemplateDrop(templateId, position);
    };

    canvas.addEventListener("dragover", handleDragOver);

    canvas.addEventListener("drop", handleDrop);

    return () => {
      canvas.removeEventListener("dragover", handleDragOver);

      canvas.removeEventListener("drop", handleDrop);
    };
  }, [camera, gl, onTemplateDrag, onTemplateDrop]);

  return null;
}
