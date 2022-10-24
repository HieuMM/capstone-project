var listYardContainer = document.querySelector("#listYard");
var listYardItem = listYardContainer.querySelectorAll(".yard-container");

var yardArr = [];
listYardItem.forEach((e) => {
    yardArr.push({
        'name': e.querySelector('.yardName').textContent,
        'district' : e.querySelector('#district-name').textContent,
        'htmlEle': e
    })
})

function sortPitch() {
    var sorthBy = $("#sort-by").val();
    listYardContainer.innerHTML = '';
    var sortUp = $("#sort-btn").hasClass("fa-sort-amount-up");
    yardArr.sort((a, b) => {
        var firstEle = sorthBy == 'name' ? a.name : a.district;
        var secondEle = sorthBy == 'name' ? b.name : b.district;
        console.log(firstEle)
        if (firstEle < secondEle) {
            return sortUp ? -1 : 1;
        }
        if (firstEle > secondEle) {
            return sortUp ? 1 : -1;
        }
        return 0;
    })
    yardArr.forEach((e) => {
        listYardContainer.innerHTML += `<div class="yard-container col-md-6 col-lg-3 d-flex align-items-stretch mb-5 mb-lg-0">
                                            <div class="card" style="width: 18rem;">
                                                <img class="card-img-top" src="${e.htmlEle.getElementsByTagName('img')[0].src}" 
                                                style="height: 10rem; object-fit: cover" alt="Card image cap">
                                                <div class="card-body">
                                                    <h5 class="card-title yardName" >${e.htmlEle.querySelector('.yardName').textContent}</h5>
                                                    <p id="district-name" class="card-text " >${e.htmlEle.getElementsByTagName('p')[0].textContent}</p>
                                                    <a href="${e.htmlEle.getElementsByTagName('a')[0]}"
                                                       class="btn btn-secondary btn-lg btn-block btn-custom"
                                                       style="background:#ffc107"><span>Xem sân</span>
                                                       <i class="fa fa-angle-double-right pl-2"></i>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>`;
    });

}

