let goodsDetail = function (goods) {
    let goodsDetail = JSON.parse(goods.goods_detail);
    goodsDetail = goodsDetail.content.replace(/data\-lazyload=/gm,'src=');

    let tpl = ` <img src="${goods.goods_img}" alt="" class="img">
                <div class="subject">
                    <div class="p_price">
                        <h3>￥：${goods.goods_price}</h3>
                    </div>
                    <h3 class="name">${goods.goods_name}</h3>
                    <div><h3 class="description">
                        ${goods.description}
                    </h3> </div>
                    <div>
                    <h3 class="detail">${goodsDetail}</h3>
                    </div>

                </div>`
    $("#goodsDetail").append($(tpl));

}

let queryDetail = (function (id) {
    $.ajax({
            url: "detail",
            data: {
                id: id,
            },
            dataType: "json",
            success: function (resp) {
                goodsDetail(resp)
            },
        }
    )
});

$(() => {
    // 获取当确登录状态
    $.get('getUserInfo', user => {
        if (!user || typeof user !== 'object') {

            var target = $("#car");
            var target1 = $("#carDetail")
            target.html("存入购物车").attr("href", "login.html");
            target1.html("打开购物车").attr("href", "login.html");
            console.log('Please login first');
        } else {
            saveInCar()

        }
    });
});

let saveInCar = function () {
    let gid = (new URL(location.href).searchParams.get('goods_id'))

    $('#car').click(function (event) {
        $.ajax({
            url: "insert",
            data: {
                gid: gid
            }, success: function (data) {
                console.log(data);
                if (data.indexOf('successfully')>-1){
                    alert("已存入购物车");
                    event.preventDefault();
                }

            }


        })
    })
}
$(function () {

    let id = (new URL(location.href).searchParams.get('goods_id'))
    queryDetail(id);

    saveInCar();

})