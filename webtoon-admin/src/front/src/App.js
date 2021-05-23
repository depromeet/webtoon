import * as React from "react";
import {Admin, Resource, ListGuesser, EditGuesser} from 'react-admin';
import customDataProvider from './support/customDataProvider';

import Dashboard from './page/dashboard/Dashboard';

import WebtoonIcon from '@material-ui/icons/Book';
import {WebtoonList} from './page/webtoons/webtoons';

console.log(process.env.REACT_APP_API_URL);
const dataProvider = customDataProvider(process.env.REACT_APP_API_URL);
const App = () => (
        <Admin title="투니투니 어드민" dashboard={Dashboard} /*authProvider={authProvider}*/ dataProvider={dataProvider}>
            <Resource options={{label: '웹툰'}} name="webtoons" list={WebtoonList} icon={WebtoonIcon}/>
        </Admin>
);

export default App;
