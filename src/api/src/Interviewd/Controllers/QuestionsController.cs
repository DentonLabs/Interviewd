using System.Collections.Generic;
using System.Threading.Tasks;
using Interviewd.Application;
using Interviewd.Application.Dto;
using Microsoft.AspNetCore.JsonPatch;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.ModelBinding;

namespace Interviewd.Controllers
{
    [Route("questions")]
    public class QuestionsController : Controller
    {
        private readonly IQuestionManager _QuestionManager;

        public QuestionsController(IQuestionManager questionManager)
        {
            _QuestionManager = questionManager;
        }

        [HttpGet]
        public async Task<IEnumerable<QuestionDto>> GetQuestions()
        {
            return await _QuestionManager.GetQuestions();
        }

        [HttpGet]
        [Route("{id}")]
        public async Task<QuestionDto> GetQuestion([FromRoute]string id)
        {
            return await _QuestionManager.GetQuestion(id);
        }

        [HttpPost]
        public async Task<QuestionDto> PostQuestion([FromBody]QuestionDto questionDto)
        {
            return await _QuestionManager.CreateQuestion(questionDto);
        }

        [HttpPatch]
        [Route("{id}")]
        public async Task<QuestionDto> PatchQuestion([FromRoute] string id, [FromBody] JsonPatchDocument<QuestionDto> patch)
        {
            // Todo(AD): ModelState?
            var question = await _QuestionManager.GetQuestion(id);
            patch.ApplyTo(question);
            return await _QuestionManager.UpdateQuestion(question);
        }
    }
}