package com.ifood.sideb.sugestionservice.service;

import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.ifood.sideb.sugestionservice.domain.PlayList;
import com.ifood.sideb.sugestionservice.domain.music.Items;
import com.ifood.sideb.sugestionservice.domain.music.PlayListResponse;;

public class PlayListMapper {

	public static PlayList of(PlayListResponse playListResponse, String message) {
		return PlayList.builder()
				.message(message)
				.results(playListResponse.getTracks()
						.getItems().stream()
						.map(Items::getName)
						.collect(Collectors.toList()))
				.build();
	}
	
	public static PlayList playListDefault() {
		return PlayList.builder()
				.results(Lists.newArrayList(
		        "Só Quero Ver Voce / There Is Only One - Ao Vivo",
		        "Isaias 9",
		        "O Dia Que Será Pra Sempre",
		        "Isaías 9 - Ao Vivo",
		        "Até Que A Casa Esteja Cheia",
		        "Pisaduras - Ao Vivo",
		        "Dia Quente",
		        "Na Gravidade da Presença",
		        "No Teu Jardim - Ao Vivo",
		        "Isaías 9 - Ao Vivo",
		        "Nível Raso",
		        "Minha Maior Riqueza",
		        "Todo Dia Até Morrer",
		        "Saudades de Casa",
		        "Tão Sublime - Ao Vivo",
		        "Mar de Vidro - Ao Vivo",
		        "Na Gravidade da Presença",
		        "Avançar (feat. Rodolfo Abrantes)",
		        "Parecido Contigo",
		        "Uma Luz Que Não Pode Se Apagar"))
			.build();
	}
}
