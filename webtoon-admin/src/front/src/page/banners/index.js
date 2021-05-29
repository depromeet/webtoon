import {ShowGuesser} from "react-admin";
import {BannerCreate, BannerEdit} from "./BannerCreateEdit";
import {BannerList} from "./BannerList";

import BannerIcon from "@material-ui/icons/ViewCarousel";

export default {
    options: {label: '배너'},
    create: BannerCreate,
    edit: BannerEdit,
    list: BannerList,
    show: ShowGuesser,
    icon: BannerIcon
};
