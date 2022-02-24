let renderCar = function (goods) {
    let tpl = `<li><img src="${goods.goods_img}" alt="">
        <h3>${goods.description}</h3>
        <div class="p_price">
            <h3>￥:${goods.goods_price}</h3>
        </div>
        <div class="delete">
        <button class="deleteOne" data-gid="${goods.goods_id}">删除</button>
</div>
        <div class="num">
            数量<input type="text" class="count" data-gid="${goods.goods_id}">
        </div></li>`;
    $('#shoppingCarContainer').append($(tpl))
}
let renderList = function (list) {
    (list || []).forEach(renderCar);
}

let queryList = function () {
    $.ajax({
        url: "car",
        dataType: "json",
        success: function (resp) {
            console.log(resp)
            renderList(resp)
            btnDelete();
        }
    })
}


//todo gid可以通过queryList返回值来取吗
/*let updateNum = function () {
    $('#order').click(function () {
let gid =
        $.ajax({
            url: "updateNum",
            data: {
                //todo
                gid: ,
                num: $('#count').val()
            }, success: function (resp) {
                console.log(resp);
            }
        })
    })
}*/
let deleteGoodAll = function () {
    $.ajax({
        url: "deleteAll",
        success: function (resp) {
            console.log(resp)
        }
    })
}


let deleteGoods = function (gid) {
//删除一条数据

    $.ajax({
        url: "delete",
        data: {
            gid: gid
        }, success: function (resp) {
            console.log(resp)
        }
    })
}
let btnDelete = function () {
    $('.deleteOne').click(function () {
debugger;
        let gid = $('.deleteOne').attr('data-gid');
        alert(gid)
       // console.log(gid)
        deleteGoods(gid)
        $(this).closest('li').remove();


    })
}

let createOrder = function () {
    $('#order').click(function () {

        let carGoods = [];
        $('.count').each(function (index, item) {
            let num = $(item);
            carGoods.push({
                gid: num.data('gid'),
                num: num.val()
            });
            console.log(carGoods);
            deleteGoodAll();
        });

        $.ajax({
            url: "orderSave",
            data: {
                list: JSON.stringify(carGoods)
            },
            success: function (resp) {
                console.log(resp)
                if (resp.indexOf("success" > -1)) {
                    alert("订单已生成！！！")
                    location.replace("order.html")

                }

            }
        })
    })
}

$(function () {
    queryList();
    debugger;


    createOrder();
});