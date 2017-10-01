using System.Collections.Generic;
using System.Threading.Tasks;
using Interviewd.Application;
using Interviewd.Application.Dto;
using Microsoft.AspNetCore.JsonPatch;
using Microsoft.AspNetCore.Mvc;

namespace Interviewd.Controllers
{
    [Route("templates")]
    public class InterviewTemplatesController : Controller
    {
        private readonly IInterviewTemplateManager _InterviewTemplateManager;

        public InterviewTemplatesController(
            IInterviewTemplateManager interviewTemplateManager)
        {
            _InterviewTemplateManager = interviewTemplateManager;
        }

        [HttpPost]
        public async Task<InterviewTemplateDto> PostInterviewTemplate([FromBody] InterviewTemplateDto interviewTemplateDto)
        {
            return await _InterviewTemplateManager.CreateInterviewTemplate(interviewTemplateDto);
        }

        [HttpGet]
        [Route("{id}")]
        public async Task<InterviewTemplateDto> GetInterviewTemplate([FromRoute] string id)
        {
            return await _InterviewTemplateManager.GetInterviewTemplate(id);
        }

        [HttpGet]
        public async Task<IEnumerable<InterviewTemplateDto>> GetInterviewTemplates()
        {
            return await _InterviewTemplateManager.GetInterviewTemplates();
        }

        [HttpPatch]
        public async Task<InterviewTemplateDto> PatchInterviewTemplate(
            [FromRoute] string id,
            [FromBody] JsonPatchDocument<InterviewTemplateDto> patchRequest)
        {
            var interviewTemplateDto = await _InterviewTemplateManager.GetInterviewTemplate(id);
            patchRequest.ApplyTo(interviewTemplateDto);

            return await _InterviewTemplateManager.UpdateInterviewTemplate(interviewTemplateDto);
        }
    }
}