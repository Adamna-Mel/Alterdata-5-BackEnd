package br.com.alterdata.pack.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alterdata.pack.exception.BadRequestException;
import br.com.alterdata.pack.exception.NotFoundException;
import br.com.alterdata.pack.model.Time;
import br.com.alterdata.pack.repository.TimeRepository;


@Service
public class TimeService {

    @Autowired
    private TimeRepository _repositorioTime;

    public List<Time> obterTodos() {
        return this._repositorioTime.findAll();    
    }

    public Optional<Time> obterPorId(Long id) {
        Optional<Time> encontrado = _repositorioTime.findById(id);

        if(!encontrado.isPresent()) {
            throw new NotFoundException("Não foi encontrado time com o ID: " + id);
        }
        return encontrado;
    }

    public Optional<Time> obterPorNome(String nome) {
        Optional<Time> encontrado = _repositorioTime.findByNomeContainingIgnoreCase(nome);

        if(!encontrado.isPresent()) {
            throw new NotFoundException("Não foi encontrado time com o nome: " + nome);
        }
        return encontrado;
    }

    public Time adicionar(Time time) {
        time.setId(null);

        if(time.getNome() == "" || time.getNome() == null){
            throw new BadRequestException("Nome não pode ser nulo!");
        }
        Time novoTime = _repositorioTime.save(time);
        return novoTime;
    }
    
    public Time atualizar(Long id, Time time) {
        time.setId(id);

        if(time.getNome() == "" || time.getNome() == null){
            throw new BadRequestException("Nome não pode ser nulo!");
        }
        Time timeAtualizado = _repositorioTime.save(time);
        return timeAtualizado;
    }

    public void deletar(Long id) {
        Optional<Time> existe = _repositorioTime.findById(id);
		
        if(!existe.isPresent()) {
			throw new NotFoundException("Não existe time com o id informado: " + id);	
		}
        this._repositorioTime.deleteById(id);
    }
}