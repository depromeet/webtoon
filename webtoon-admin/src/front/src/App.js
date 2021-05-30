import * as React from "react";
import {Admin, Resource} from 'react-admin';
import customDataProvider from './support/customDataProvider';
import authProvider from './support/customAuthProvider';

import Dashboard from './page/dashboard/Dashboard';

import banners from './page/banners';
import webtoons from './page/webtoons';

const dataProvider = customDataProvider(process.env.REACT_APP_API_URL);
const App = () => (
        <Admin title="투니투니 어드민" dashboard={Dashboard} authProvider={authProvider} dataProvider={dataProvider}>
            <Resource name="webtoons" {...webtoons}/>
            <Resource name="banners" {...banners}/>
        </Admin>
);

export default App;
