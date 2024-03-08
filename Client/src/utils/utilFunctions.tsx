export function getImageSrc(base64Image: any): string {
  const imageData = Uint8Array.from(atob(base64Image), (char) =>
    char.charCodeAt(0)
  );

  let imageType = "jpeg";
  if (
    imageData[0] === 0x89 &&
    imageData[1] === 0x50 &&
    imageData[2] === 0x4e &&
    imageData[3] === 0x47
  ) {
    imageType = "png";
  }

  return `data:image/${imageType};base64,${base64Image}`;
}
