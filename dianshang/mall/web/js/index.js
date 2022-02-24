let renderGoods = function (goods) {
    let tpl = `<li class="goods-item">
                <a href="detail.html?goods_id=${goods.goods_id}">
                    <img src="${goods.goods_img}" alt="">
                </a>
                <div class="subject">
                    <div class="p_price">
                        <h3>￥：${goods.goods_price}</h3>
                    </div>
                    <h3 class="name">${goods.goods_name}</h3>
                    <div><a href="detail.html?goods_id=${goods.goods_id}" class="description">
                        ${goods.description.substr(0, 28)}
                    </a> </div>
                </div>
            </li>`;
    $("#goodsList").append($(tpl));
}


let renderList = function (list) {
    (list || []).forEach(renderGoods);
}

let queryList = function (keywords) {
    $.ajax({
        url: "query",
        data: {
            "name": keywords
        },
        dataType: "json",
        success: function (resp) {
            renderList(resp);

        }
    })
};

$(() => {
    // 获取当确登录状态
    $.get('getUserInfo', user => {
        if (!user || typeof user !== 'object') {
            $('#car').hide();
            $('#order').hide();
            var target = $("#login");

            target.html("登录").attr("href", "login.html");

            console.log('Please login first');
        } else {
            $('#login').hide();

        }
    });
});
$(function () {
    queryList('nike');

    $('#btn').click(function () {
        $("#goodsList").empty();
        let text = $("#textGoods").val();
        queryList(text);
    });

});