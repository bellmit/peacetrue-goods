import * as React from "react";
import {Resource} from "react-admin";

import {GoodsList} from './List';
import {GoodsCreate} from './Create';
import {GoodsEdit} from './Edit';
import {GoodsShow} from './Show';
import ShopIcon from '@material-ui/icons/Shop';

export const Goods = {list: GoodsList, create: GoodsCreate, edit: GoodsEdit, show: GoodsShow};
export const GoodsResource = <Resource name="goods" icon={ShopIcon} {...Goods} />;
export default GoodsResource;

export * from './Messages'
export * from "./DisplayButton"
export * from "./TransformDataBuilder"
