import * as React from 'react';
import {ReferenceField, RichTextField, ShowProps, SimpleShowLayout, TextField} from 'react-admin';
import {PeaceLabeledFileField, PeaceLabeledImageField, PeaceShow} from "peacetrue-react-admin";
import {MerchantCreateModifyFields} from "peacetrue-merchant";

export const GoodsShow = (props: ShowProps) => {
  console.info('GoodsShow:', props);
  return (
    <PeaceShow {...props}>
      <SimpleShowLayout>
        <PeaceLabeledImageField source="coverImageUrls"/>
        <PeaceLabeledFileField source="coverVideoUrls"/>
        <TextField source="name"/>
        <RichTextField source="detail"/>
        <ReferenceField source={"display"} reference="enums/goodsDisplay" link={false}>
          <TextField source={"name"}/>
        </ReferenceField>
        <TextField source={"price"}/>
        <TextField source={"orderCount"}/>
        <TextField source={"remark"}/>
        <TextField source={"serialNumber"}/>
        {MerchantCreateModifyFields}
      </SimpleShowLayout>
    </PeaceShow>
  );
};
