import * as React from 'react';
import {
  Create,
  CreateProps,
  FileField,
  FileInput,
  ImageField,
  ImageInput,
  maxLength,
  NumberInput,
  required,
  SimpleForm,
  TextInput,
  useDataProvider
} from 'react-admin';
import RichTextInput from "ra-input-rich-text";
import {PeaceRichTextInput} from "peacetrue-react-admin";
import {TransformDataBuilder} from "./TransformDataBuilder";

export const GoodsCreate = (props: CreateProps) => {
  console.info('GoodsCreate:', props);
  let dataProvider = useDataProvider();
  //initialValues={{name: '1', detail: '1', price: 1, display: 0}}
  return (
    <Create transform={TransformDataBuilder(dataProvider)} {...props}>
      <SimpleForm>
        <ImageInput source="coverImages" accept='image/*' multiple
                    minSize={1} maxSize={5 * 1024 * 1024} validate={[required(),]}
                    placeholder={'点击或拖拽上传，支持最大 5M 的图片文件'}
        >
          <ImageField source="src" title="title"/>
        </ImageInput>
        <FileInput source="coverVideos" accept={'video/*'} multiple
                   minSize={1} maxSize={5 * 1024 * 1024} validate={[required(),]}
                   placeholder={'点击或拖拽上传，支持最大 5M 的视频文件'}
        >
          <FileField source="src" title="title" target={'_blank'}/>
        </FileInput>
        <TextInput source="name" validate={[required(), maxLength(255)]} resettable fullWidth/>
        <RichTextInput source="detail" {...PeaceRichTextInput.options}
                       validate={[required(), maxLength(Math.pow(1024, 4) - 1)]}
                       resettable fullWidth/>
        <NumberInput source="price" min={0} max={Math.pow(10, 10) - 0.01} step={0.01}
                     validate={[required()]}/>
        <TextInput source="remark" validate={[maxLength(255)]} resettable fullWidth multiline/>
        <NumberInput source="serialNumber" min={1} max={Number.MAX_VALUE} step={1}/>
      </SimpleForm>
    </Create>
  );
};
