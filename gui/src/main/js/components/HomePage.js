import React from "react";

import '../../css/style.css';
import '../../css/loadingSpinner.css';

/**
 * Домашняя страница.
 *
 * @param props свойства
 *
 */
export default function HomePage(props) {
    async function parseItemPage() {
        document.getElementById("itemWrapper").style.display="none";
        document.getElementById("errorWrapper").style.display="none";
        document.getElementById('parsingOverlay').style.visibility = 'visible';
        const url = document.getElementById("itemUrl").value;
        let response = await fetch('/parse', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify({
                "itemPage": url
            })
        });
        let result = await response.json();
        document.getElementById('parsingOverlay').style.visibility = 'hidden';
        let error = result.error;
        if (error) {
            document.getElementById("errorMessage").textContent = error;
            document.getElementById("errorWrapper").style.display="block";
        } else {
            document.getElementById("itemName").textContent = result.name;
            document.getElementById("itemPrice").textContent = result.price;
            document.getElementById("itemSeller").textContent = result.seller;
            document.getElementById("itemWrapper").style.display="block";
        }
    }

    return (
        <div>
            <div id="actionZone">
                <input id="itemUrl" name="itemUrl" type="url" placeholder="Ссылка на карточку товара" />
                <input id="actionButton" type="button" value="Распарсить" onClick={parseItemPage}/>
            </div>
            <div id="itemWrapper">
                <div id="itemInfo">
                    <span className="infoTitle">Наименование:</span><span id="itemName" />
                    <span className="infoTitle">Цена:</span><span id="itemPrice" />
                    <span className="infoTitle">Продавец:</span><span id="itemSeller" />
                </div>
            </div>
            <div id="errorWrapper">
                <div id="errorInfo">
                    <span id="errorMessage" />
                </div>
            </div>
            <div id="parsingOverlay" className="parsingOverlay">
                Loading
            </div>
        </div>
    );
}