package br.com.alterdata.pack.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.alterdata.pack.model.Equipe;
import br.com.alterdata.pack.model.Usuario;
import br.com.alterdata.pack.service.EquipeService;
import br.com.alterdata.pack.shared.EquipeDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin("*")
@Api("API PACK - Sistema de Status e Papéis")
@RestController
@RequestMapping("/api/equipes")
public class EquipeController {

    @Autowired
    EquipeService _equipeUsuario;
  
    //#region GET

    @ApiOperation(value = "Retorna todos as equipes cadastradas")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista de equipes encontrada com sucesso :)"),
        @ApiResponse(code = 204, message = "Não existe equipe cadastrado :("),
        @ApiResponse(code = 403, message=  "Você não tem permissão para isso meu consagrado :("),
        @ApiResponse(code = 500, message = "Vish quinhetão, da uma olhadinha no código ;-;") 
    })
    @GetMapping
    public ResponseEntity<List<EquipeDto>> obterTodos(@PageableDefault(page=0, size=4) Pageable pageable) {
        return ResponseEntity.ok(_equipeUsuario.obterTodos(pageable));
    }
    

    @ApiOperation(value = "Filtra as equipes cadastradas de acordo com o Id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Equipe encontrada com sucesso :)"),
        @ApiResponse(code = 404, message = "Não existe equipe com esse Id :("),
        @ApiResponse(code = 403, message=  "Você não tem permissão para isso meu consagrado :("),
        @ApiResponse(code = 500, message = "Vish quinhetão, da uma olhadinha no código ;-;") 
    })
    @GetMapping("/{id}")
    public ResponseEntity<Optional<EquipeDto>> obterPorId(@PathVariable(value = "id") Long id) {
        Optional<EquipeDto> equipe = _equipeUsuario.obterPorId(id);
        return  new ResponseEntity<>(equipe, HttpStatus.OK);
    }


    @ApiOperation(value = "Filtra as equipes cadastradas de acordo com o nome")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Equipe encontrada com sucesso :)"),
        @ApiResponse(code = 404, message = "Não existe equipe com esse nome :("),
        @ApiResponse(code = 403, message=  "Você não tem permissão para isso meu consagrado :("),
        @ApiResponse(code = 500, message = "Vish quinhetão, da uma olhadinha no código ;-;") 
    })
    @GetMapping("/nome/{nome}")
	public ResponseEntity<List<Equipe>> obterPorNome(@PathVariable ("nome") String nome) {
		return new ResponseEntity<>(_equipeUsuario.obterPorNome(nome), HttpStatus.OK);
	}


    @ApiOperation(value = "Filtra os usuários da equipe por login")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Equipe encontrada com sucesso :)"),
        @ApiResponse(code = 404, message = "Não existe usuario com esse login :("),
        @ApiResponse(code = 403, message=  "Você não tem permissão para isso meu consagrado :("),
        @ApiResponse(code = 500, message = "Vish quinhetão, da uma olhadinha no código ;-;") 
    })
    @GetMapping("/{id}/login/{login}")
	public ResponseEntity<List<Usuario>> obterUsuariosPorLogin(@PathVariable ("id") Long id, @PathVariable ("login") String login) {
		return new ResponseEntity<>(_equipeUsuario.obterUsuariosPorLogin(id,login), HttpStatus.OK);
	}
    

    @ApiOperation("Retorna o avatar da equipe")
     @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Avatar retornado com sucesso :)"),
        @ApiResponse(code = 404, message = "Não existe usuario com esse id :("),
        @ApiResponse(code = 403, message=  "Você não tem permissão para isso meu consagrado :("),
        @ApiResponse(code = 500, message = "Vish quinhetão, da uma olhadinha no código ;-;") 
    })
    @GetMapping("/avatar/{id}")
    public ResponseEntity<byte[]> retornarAvatar(@PathVariable(value = "id") Long id) throws IOException{
        return new ResponseEntity<>(_equipeUsuario.retornarAvatar(id), HttpStatus.OK);
    }

    //#endregion
    //#region POST

    @ApiOperation(value = "Cadastra uma nova Equipe")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Equipe criada com sucesso :)"),
        @ApiResponse(code = 400, message = "Informação invalida :o"),
        @ApiResponse(code = 404, message = "Não existe equipe com esse id :("),
        @ApiResponse(code = 415, message = "Mídia não suportada, vá com calma jovem ;)"),
        @ApiResponse(code = 403, message=  "Você não tem permissão para isso meu consagrado :("),
        @ApiResponse(code = 500, message = "Vish quinhetão, da uma olhadinha no código ;-;") 
    })
    @PostMapping
    public ResponseEntity<Equipe> adicionar(EquipeDto equipe) {
        Equipe novaEquipe = _equipeUsuario.criarEquipe(equipe);
        return new ResponseEntity<>(novaEquipe, HttpStatus.CREATED );
    }

    //#endregion
    //#region PUT

    @ApiOperation(value = "Atualiza as informações de uma equipe de acordo com o id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Equipe atualizada com sucesso :)"),
        @ApiResponse(code = 400, message = "Informação invalida :o"),
        @ApiResponse(code = 404, message = "Não existe equipe com esse Id :("),
        @ApiResponse(code = 403, message=  "Você não tem permissão para isso meu consagrado :("),
        @ApiResponse(code = 500, message = "Vish quinhetão, da uma olhadinha no código ;-;") 
    })
    @PutMapping("/{id}")
    public ResponseEntity<Equipe> atualizar(@PathVariable(value = "id") Long id, @RequestBody EquipeDto equipe) {
        Equipe equipeAtualizado = _equipeUsuario.atualizar(id, equipe);
        return new ResponseEntity<>(equipeAtualizado, HttpStatus.OK);
    }

    //#endregion
    //#region PATCH

    @ApiOperation(value = "Alterar avatar")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Avatar atualizado com sucesso :)"),
        @ApiResponse(code = 400, message = "Informação invalida :o"),
        @ApiResponse(code = 404, message = "Não existe equipe com esse Id :("),
        @ApiResponse(code = 403, message=  "Você não tem permissão para isso meu consagrado :("),
        @ApiResponse(code = 415, message = "Mídia não suportada, vá com calma jovem ;)"),
        @ApiResponse(code = 500, message = "Vish quinhetão, da uma olhadinha no código ;-;") 
    })
    @PatchMapping("alterar-avatar/{id}")
    public ResponseEntity<Equipe> editarAvatar(@PathVariable(value = "id") Long id, @RequestParam("img") MultipartFile arquivo) {
        Equipe novoAvatarEquipe = _equipeUsuario.editarAvatar(id, arquivo);     
        return new ResponseEntity<>(novoAvatarEquipe, HttpStatus.OK);
    }

    //#endregion
    //#region DELETE

    @ApiOperation(value = "Deleta uma equipe de acordo com o id")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Equipe removida com sucesso :)"),
        @ApiResponse(code = 400, message = "Informação invalida :o"),
        @ApiResponse(code = 404, message = "Não existe equipe com esse Id :("),
        @ApiResponse(code = 403, message=  "Você não tem permissão para isso meu consagrado :("),
        @ApiResponse(code = 500, message = "Vish quinhetão, da uma olhadinha no código ;-;") 
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable(value = "id") Long id) {
        _equipeUsuario.deletar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //#endregion

}
