import * as React from "react";
import {
    TextField, Edit, SimpleForm, SelectInput, TextInput, NumberInput, DateTimeInput, Create
} from 'react-admin';

export const BannerEdit = props => {

    const transform = data => {
        return ({
            ...data,
            webtoonId: `${data.webtoon.id}`
        });
    };

    return (
            <Edit {...props} transform={transform}>
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
};


export const BannerCreate = props => {
    return (
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
                    <NumberInput source="webtoonId"/>
                    <NumberInput source="priority" label="우선순위"/>
                    <DateTimeInput source="displayBeginDateTime" label="배너시작일시"/>
                    <DateTimeInput source="displayEndDateTime" label="배너종료일시"/>
                </SimpleForm>
            </Create>
    );
};

