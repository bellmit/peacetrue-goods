import * as React from 'react';
import {
  Datagrid,
  EditButton,
  Filter,
  ListProps,
  ReferenceField,
  ReferenceInput,
  SelectInput,
  TextField,
  TextInput,
  useGetIdentity
} from 'react-admin';
import {DisplayButton} from "./DisplayButton";
import {MerchantCreateFields} from "peacetrue-merchant";
import {UserCreatedTimeFilter} from "peacetrue-user";
import {PeaceDeleteButton, PeaceEnv, PeaceList} from "peacetrue-react-admin";

const Filters = (props: any) => {
  return (
    <Filter {...props}>
      <TextInput source="name" allowEmpty alwaysOn resettable/>
      <ReferenceInput source="display" reference="enums/goodsDisplay" allowEmpty alwaysOn>
        <SelectInput source="code" optionText="name" resettable/>
      </ReferenceInput>
      {PeaceEnv.isPlatform && <ReferenceInput label={'商家'} source="creatorId" reference="merchants" allowEmpty alwaysOn>
          <SelectInput optionText="username" resettable/>
      </ReferenceInput>}
      {UserCreatedTimeFilter}
    </Filter>
  );
};

export const GoodsList = (props: ListProps) => {
  console.info("GoodsList:", props);
  const {identity, loading: identityLoading} = useGetIdentity();
  if (identityLoading) return null;
  const creatorIdFilter: Record<string, any> = {};
  if (PeaceEnv.isMerchant) creatorIdFilter.creatorId = identity?.id;
  return (
    <PeaceList filters={<Filters/>}
               sort={{field: 'serialNumber', order: 'DESC'}}
               filterDefaultValues={{...creatorIdFilter}}
               {...props}>
      <Datagrid rowClick="show">
        <TextField source="name"/>
        <ReferenceField source="display" reference="enums/goodsDisplay" link={false}>
          <TextField source={'name'}/>
        </ReferenceField>
        <TextField source="price"/>
        <TextField source="orderCount"/>
        <TextField source="serialNumber"/>
        {MerchantCreateFields}
        <DisplayButton/>
        <EditButton/>
        <PeaceDeleteButton/>
      </Datagrid>
    </PeaceList>
  )
};
