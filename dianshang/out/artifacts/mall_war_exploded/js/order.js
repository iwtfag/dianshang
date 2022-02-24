/**
 * goods的含义
 * gid获取
 * detail页做查询要把index页复制吗
 * @param goods
 */

let renderGoods = function (goods) {
    let tpl = `<li class="orderItem">
                <h3 date-oid="${goods.id_order}" class="oidList">订单号：${goods.id_order}</h3>
              <a href="detail.html?goods_id=${goods.goods_id}">
                  <img src="${goods.goods_img}" alt="">
              </a>
              <h4>订单生成时间：${goods.gmt_create}</h4>
              <h3>${goods.goods_name}</h3>
              <h3>${goods.description}</h3>
             <div>× ${goods.goods_num}</div>
             <div></div>
          </li>`
    $('#goodsList').append($(tpl))
}
let renderList = function (list) {
    (list || []).forEach(renderGoods);
}

let queryOrder = function () {
    $.ajax({
        url: "queryOrder",
        dataType: "json",
        success: function (resp) {
            renderList(resp)
        }
    })
}


    $(function () {
        queryOrder();

    })
