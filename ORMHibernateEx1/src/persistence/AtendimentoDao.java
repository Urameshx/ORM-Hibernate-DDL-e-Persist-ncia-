package persistence;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import model.Atendimento;
import model.Cliente;

public class AtendimentoDao implements IAtendimentoDAO {

private SessionFactory sf;
	
	public AtendimentoDao(SessionFactory sf) {
		this.sf = sf;
	}
	
	@Override
	public void insere(Atendimento atend) {
		EntityManager entityManager = sf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(atend);
		transaction.commit();
	}

	@Override
	public Atendimento selectOne(Atendimento atend) {
		EntityManager entityManager = sf.createEntityManager();
		atend = entityManager.find(Atendimento.class, atend.getAtendente());
		atend = entityManager.find(Atendimento.class, atend.getCliente());
		atend = entityManager.find(Atendimento.class, atend.getDataHora());		;
		return atend;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Atendimento> selectOneCliente(Atendimento atend) {
		List<Atendimento> atendimentos = new ArrayList<Atendimento>();
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT cpf_cliente ");
		buffer.append("FROM atendimento ");
		buffer.append("ORDER BY data_hora_atendimento ");
		EntityManager entityManager = sf.createEntityManager();
		Query query = entityManager.createNativeQuery(buffer.toString());
		List<Object[]> lista = query.getResultList();
		for (Object[] obj : lista) {
			Atendimento atend1 = new Atendimento();
			atend1.getCliente(obj[0].toString());
			
			atendimentos.add(atend1);
		}
		
		return atendimentos;
	}

	@Override
	public List<Atendimento> selectOneAtendente(Atendimento atend) {
		List<Atendimento> atendimentos = new ArrayList<Atendimento>();
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT id_atendente ");
		buffer.append("FROM atendimento ");
		buffer.append("ORDER BY data_hora_atendimento ");
		EntityManager entityManager = sf.createEntityManager();
		Query query = entityManager.createNativeQuery(buffer.toString());
		List<Object[]> lista = query.getResultList();
		for (Object[] obj : lista) {
			Atendimento atend2 = new Atendimento();
			atend2.getAtendente(obj[0].toString());
			
			atendimentos.add(atend2);
		}
		
		return atendimentos;
	}

	@Override
	public List<Atendimento> selectAll() {
		List<Atendimento> atendimentos = new ArrayList<Atendimento>();
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT cpf_cliente, id_atendente, data_hora_atendimento ");
		buffer.append("FROM atendimento ");
		buffer.append("ORDER BY data_hora_atendimento ");
		EntityManager entityManager = sf.createEntityManager();
		Query query = entityManager.createNativeQuery(buffer.toString());
		List<Object[]> lista = query.getResultList();
		for (Object[] obj : lista) {
			Atendimento atend3 = new Atendimento();
			atend3.getCliente(obj[0].toString());
			atend3.getAtendente(obj[1].toString());
			atend3.getDataHora(obj[2].toString());			
			atendimentos.add(atend3);
		}
		
		return atendimentos;
	}


}
