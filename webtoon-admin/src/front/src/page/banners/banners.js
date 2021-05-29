import * as React from "react";
import {
    List,
    Datagrid,
    TextField,
    NumberField,
    DateField
} from 'react-admin';

import {
    Create,
    Edit,
    SimpleForm,
    TextInput,
    SelectInput,
    NumberInput,
    DateTimeInput
} from 'react-admin';

export const BannerList = props => (
        <List {...props}>
            <Datagrid rowClick="edit">
                <TextField source="id"/>
                <TextField source="bannerInventory" label="인벤토리"/>
                <TextField source="caption" label="표기문구"/>
                <NumberField source="webtoon.id" label="매핑 웹툰 ID"/>
                <NumberField source="webtoon.title" label="매핑 웹툰타이틀"/>
                <NumberField source="priority" label="우선순위"/>
                <DateField source="displayBeginDateTime" label="배너시작일시"/>
                <DateField source="displayEndDateTime" label="배너종료일시"/>
            </Datagrid>
        </List>
);

export const BannerEdit = props => (
        <Edit {...props}>
            <SimpleForm>
                <TextField source="id"/>
                <SelectInput
                        source="bannerInventory"
                        label="인벤토리"
                        choices={[
                            {id: 'HOME_MAIN', name: 'HOME_MAIN'}
                        ]}
                />
                <TextInput source="caption" label="표기글"/>
                <NumberInput source="webtoon.id"/>
                <NumberInput source="priority" label="우선순위"/>
                <DateTimeInput source="displayBeginDateTime" label="배너시작일시"/>
                <DateTimeInput source="displayEndDateTime" label="배너종료일시"/>
            </SimpleForm>
        </Edit>
);


export const BannerCreate = props => (
        <Create {...props}>
            <SimpleForm>
                <SelectInput
                        source="bannerInventory"
                        label="인벤토리"
                        choices={[
                            {id: 'HOME_MAIN', name: 'HOME_MAIN'}
                        ]}
                />
                <TextInput source="caption" label="표기글"/>
                <NumberInput source="webtoon.id"/>
                <NumberInput source="priority" label="우선순위"/>
                <DateTimeInput source="displayBeginDateTime" label="배너시작일시"/>
                <DateTimeInput source="displayEndDateTime" label="배너종료일시"/>
            </SimpleForm>
        </Create>
);
