import {DataProvider} from "react-admin";
import {FileParamBuilder, fileTransformBuilder, PeaceRichTextInput, TransformDataReducer} from "peacetrue-react-admin";

export function TransformDataBuilder(dataProvider: DataProvider) {
  let date = new Date();
  let coverImage = FileParamBuilder('coverImages', `/goods/cover/image/${date.getFullYear()}/${date.getMonth()}/${date.getDate()}`, false);
  let coverVideo = FileParamBuilder('coverVideos', `/goods/cover/video/${date.getFullYear()}/${date.getMonth()}/${date.getDate()}`, false);
  let transformDataArray = [
    fileTransformBuilder(dataProvider, [coverImage, coverVideo]),
    PeaceRichTextInput.buildTransformData('detail')
  ];
  return TransformDataReducer(transformDataArray);
}
