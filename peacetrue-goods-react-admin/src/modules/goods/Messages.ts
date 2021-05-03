import {Messages as UserMessages} from "peacetrue-user";

export const GoodsMessages = {
  resources: {
    "goods": {
      name: '商品',
      fields: {
        'id': '主键',
        'coverImage': '封面图片',
        'coverImages': '封面图片',
        'coverImageUrls': '封面图片',
        'coverVideo': '封面视频',
        'coverVideos': '封面视频',
        'coverVideoUrls': '封面视频',
        'name': '名称',
        'detail': '详情',
        'display': '展示状态',
        'price': '价格(元)',
        'remark': '备注',
        'serialNumber': '序号',
        'orderCount': '下单数量',
        ...UserMessages
      },
    }
  },
  ra: {
    action: {
      'on_sale': '上架',
      'off_sale': '下架'
    }
  }
}

//['id','coverImage','coverVideo','name','detail','displayId','price','remark','serialNumber','creatorId','createdTime','modifierId','modifiedTime',]
//['主键','封面图片','封面视频','名称','简介','展示状态','价格(元)','备注','序号','创建者','创建时间','最近修改者','最近修改时间',]

export default GoodsMessages;
