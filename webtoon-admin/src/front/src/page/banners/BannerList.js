import * as React from "react";
import {
    List,
    Datagrid,
    TextField,
    NumberField,
    DateField
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
