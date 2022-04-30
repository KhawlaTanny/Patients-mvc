package ma.emsi.patientsmvc.web;

import lombok.AllArgsConstructor;
import ma.emsi.patientsmvc.entities.Medecin;
import ma.emsi.patientsmvc.entities.Patient;
import ma.emsi.patientsmvc.repositories.MedecinRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class MedecinController {
    private MedecinRepository medecinRepository;

    @GetMapping(path = "/user/medecin")
    public String Medecin(Model model,
                           @RequestParam(name = "page",defaultValue = "0") int page,
                           @RequestParam(name = "size",defaultValue = "5")int size,
                           @RequestParam(name = "keyword",defaultValue = "")String keyword
    ){
        Page<Medecin> pageMedecin=medecinRepository.findByNomContains(keyword, PageRequest.of(page, size));
        model.addAttribute("listmedecin",pageMedecin.getContent());
        model.addAttribute("pages",new int[pageMedecin.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "Medecin";
    }

    @GetMapping("/admin/medecin/delete")
    public String delete(Long id, String keyword, int page) {
        medecinRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/admin/formMedecin")
    public String formMedecin(Model model){
        model.addAttribute("Medecin",new Medecin());
        return "formMedecin";
    }
    @PostMapping(path="/admin/medecin/save")
    public String save(Model model, @Valid Medecin medecin, BindingResult bindingResult,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "") String keyword){
        if(bindingResult.hasErrors()) return "formMedecin";
        medecinRepository.save(medecin);
        return "redirect:/user/index?page"+page+"&keyword="+keyword;
    }
    @GetMapping("/admin/editMedecin")
    public String editMedecins(Model model, Long id, String keyword, int page){
        Medecin medecin=medecinRepository.findById(id).orElse(null);
        if(medecin==null) throw new RuntimeException("medecin introuvable");
        model.addAttribute("medecin",medecin);
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);
        return "editMedecins";
    }
}
