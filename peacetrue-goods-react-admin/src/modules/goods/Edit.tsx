import * as React from 'react';
import {
  EditProps,
  FileField,
  FileInput,
  ImageField,
  ImageInput,
  maxLength,
  NumberInput,
  ReferenceInput,
  ReferenceField,
  TextField,
  required,
  SelectInput,
  SimpleForm,
  TextInput,
  useDataProvider
} from 'react-admin';
import {multipleInputFormatter, PeaceEdit, PeaceRichTextInput} from "peacetrue-react-admin";
import RichTextInput from "ra-input-rich-text";
import {TransformDataBuilder} from "./TransformDataBuilder";
import {MerchantCreateModifyFields} from "peacetrue-merchant";

export const GoodsEdit = (props: EditProps) => {
  console.info('GoodsEdit:', props);
  let dataProvider = useDataProvider();
  return (
    <PeaceEdit transform={TransformDataBuilder(dataProvider)} {...props}>
      <SimpleForm>
        <ImageInput source="coverImages" accept='image/*' multiple
                    minSize={1} maxSize={5 * 1024 * 1024} validate={[required(),]}
                    placeholder={'点击或拖拽上传，支持最大 5M 的图片文件'}
                    format={multipleInputFormatter}
        >
          <ImageField source="src" title="title"/>
        </ImageInput>
        <FileInput source="coverVideos" accept={'video/*'} multiple
                   minSize={1} maxSize={5 * 1024 * 1024} validate={[required(),]}
                   placeholder={'点击或拖拽上传，支持最大 5M 的视频文件'}
                   format={multipleInputFormatter}
        >
          <FileField source="src" title="title" target={'_blank'}/>
        </FileInput>
        <TextInput source="name" validate={[required(), maxLength(255)]} resettable fullWidth/>
        <RichTextInput source="detail" {...PeaceRichTextInput.options}
                       validate={[required(), maxLength(Math.pow(1024, 4) - 1)]}
                       resettable fullWidth/>
        <NumberInput source="price" min={0} max={Math.pow(10, 10) - 0.01} step={0.01}
                     validate={[required()]}/>
        <ReferenceField source="display" reference="enums/goodsDisplay" link={false}>
          <TextField source={'name'}/>
        </ReferenceField>
        <TextField source={"orderCount"}/>
        <TextInput source="remark" validate={[maxLength(255)]} resettable fullWidth/>
        <NumberInput source="serialNumber" min={1} max={Number.MAX_VALUE} step={1}/>
        {MerchantCreateModifyFields}
      </SimpleForm>
    </PeaceEdit>
  );
};
