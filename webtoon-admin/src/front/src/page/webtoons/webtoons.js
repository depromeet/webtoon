import * as React from "react";
import {
    List,
    Datagrid,
    TextField,
    ArrayField,
    NumberField,
    SingleFieldList,
    ImageField,
    BooleanField
} from 'react-admin';

export const WebtoonList = props => (
        <List {...props}>
            <Datagrid rowClick="show">
                <TextField source="id"/>
                <TextField source="site"/>
                <ImageField source="thumbnail"/>
                <TextField source="title"/>
                <ArrayField source="authors">
                    <SingleFieldList>
                        <TextField source="name"/>
                    </SingleFieldList>
                </ArrayField>
                <TextField source="backgroundColor"/>
                <NumberField source="score"/>
                <TextField source="genres"/>
                <BooleanField source="isComplete"/>
            </Datagrid>
        </List>
);
