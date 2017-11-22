package ohtu;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ohtu.database.dto.BookHintDto;
import ohtu.database.repository.BookHintRepository;
import ohtu.model.BookHint;
import ohtu.service.BookHintService;

@Controller
public class Controllers {

    @Autowired
    private BookHintService bhService;

    @Autowired
    private BookHintRepository bhRep;

    final private int HINTS_PER_PAGE = 10;

    /**
     * Request is made to home address and all book hints are added to model.
     * @param model
     * @return View of home file is sent.
     */
    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {

    	String action = request.getParameter("action");
    	int page;

    	if (action == null) {
    	    page = 0;
        } else {
    	    page = newPageNumber(request.getParameter("page"), action);
        }

    	model.addAttribute("page", page);
        model.addAttribute("totalPages", totalNumberOfPages());
    	model.addAttribute("hints", bhService.getBookHintsInPage(page, HINTS_PER_PAGE));

        return "home";
    }

    /**
     * A request is made to the hint/add address and a book hint is added to the model.
     * @param model
     * @return Creates a view of add_hint sends it.
     */
    @GetMapping("/hint/add")
    public String addBook(Model model){
    	model.addAttribute("bookHintDto", new BookHintDto());
    	
        return "add_hint";
    }
    
    /**
     * Checks if that the book hint is added successfully.
     * @param model
     * @param bookHintDto
     * @param result
     * @return Redirects to home or creates view of add_hint and sends it.
     */
    @PostMapping("/hint/add")
    public String saveBook(Model model, @ModelAttribute @Valid BookHintDto bookHintDto, BindingResult result) {
    	if(!result.hasErrors()) {
    		bhService.createBookHint(bookHintDto);
    	
    		return "redirect:/";
    	} else {       		
    		model.addAttribute("bookHintDto", bookHintDto);
        	
            return "add_hint";
    	}
    }

    private int newPageNumber(String pageParameter, String action) {
        int page = Integer.parseInt(pageParameter);
        if (action.equals("prev")) {
            return Math.max(0, page - 1);
        } else {
            return Math.min(totalNumberOfPages(), page + 1);
        }
    }

    private int totalNumberOfPages() {
        int totalHints = bhRep.findAll().size();
        return (totalHints - 1) / HINTS_PER_PAGE;
    }


}
