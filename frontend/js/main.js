// 根據區域名稱向後端請求資料
function loadData(zone) {
    fetch(`http://127.0.0.1:8080/api/sights?zone=${zone}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log(data); // 確認資料格式
            showCards(data);   // 顯示卡片
        })
        .catch(error => {
            console.error("Error:", error);
            document.getElementById("content").innerHTML = `<p class="text-danger">資料載入失敗，請稍後再試。</p>`;
        });
}

// 載入所有景點資料
function loadAllData() {
    fetch(`http://127.0.0.1:8080/SightAPI/all`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log("全部資料：", data);
            showCards(data);
        })
        .catch(error => {
            console.error("Error:", error);
            document.getElementById("content").innerHTML = `<p class="text-danger">資料載入失敗，請稍後再試。</p>`;
        });
}

// 生成卡片內容
function showCards(data) {
    let content = "";

    if (data.length === 0) {
        content = `<p class="text-center">此區域暫無景點資料</p>`;
    }

    data.forEach((item, index) => {
        content += `
        <div class="col-12 col-md-4 mb-4">
            <div class="card h-100">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title">${item.sightName}</h5>
                    <h6 class="card-subtitle mb-2 text-muted">${item.zone}</h6>
                    <p class="card-text">類別：${item.category}</p>
                    <p class="card-text">地址：${item.address}</p>
                    <a href="https://www.google.com/maps/search/?q=${item.sightName + item.address}" target="_blank" class="btn btn-sm btn-secondary">查看地圖</a>

                    <button class="btn btn-outline-primary mt-auto" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${index}">
                        查看詳細資訊
                    </button>

                    <div class="collapse mt-2" id="collapse${index}">
                        ${item.photoUrl ? `<img src="${item.photoUrl}" class="img-fluid mb-2" onerror="this.src='https://via.placeholder.com/300x200?text=No+Image'" alt="圖片">` : ''}
                        <p class="card-text">描述: ${item.description}</p>
                    </div>
                </div>
            </div>
        </div>`;
    });

    document.getElementById("content").innerHTML = content;
}
